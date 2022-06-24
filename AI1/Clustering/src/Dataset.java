import java.util.LinkedList;
import java.util.List;

public class Dataset {
    private enum PointType {CORE,BORDER,NOISE};

    private final int DIMENSIONS;
    private final List<Point> points = new LinkedList<>();

    private class Point {
        protected final double[] features;

        public double getFeature(int i) {
            return features[i];
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i=0; i<DIMENSIONS; i++) {
                if (i>0)
                    sb.append(",");
                sb.append(features[i]);
            }
            sb.append(")");
            return sb.toString();
        }

        public Point(double[] features) {
            this.features = features;
        }
    }

    private class dbPoint extends Point {
        private PointType pointType;
        private final double radius;
        private boolean inCluster = false;

        public boolean isInCluster() {
            return inCluster;
        }

        public PointType getPointType() {
            return pointType;
        }

        public void setPointType(PointType pointType) {
            this.pointType = pointType;
        }

        public void setBorderPoint(dbPoint[] points) {
            for (dbPoint p: points) {
                if (p.getPointType() == PointType.CORE && getDistance(this,p) <= radius)
                    setPointType(PointType.BORDER);
            }
        }

        public void addToCluster(Cluster cluster, List<dbPoint> corePoints) {
            cluster.addPoint(this);
            inCluster = true;
            for (dbPoint p: corePoints)
                if (!p.isInCluster() && getDistance(this,p) <= radius)
                    p.addToCluster(cluster,corePoints);
        }

        public void joinCluster(dbPoint[] points,List<Cluster> clusters) {
            double smallestDistance = Double.MAX_VALUE;
            double distance;
            dbPoint closestP = null;
            for (dbPoint p: points) {
                if (p.getPointType() == PointType.CORE) {
                    distance = getDistance(this,p);
                    if (distance < smallestDistance) {
                        closestP = p;
                        smallestDistance = distance;
                    }
                }
            }
            for (Cluster c: clusters) {
                if(c.contains(closestP)) {
                    c.addPoint(this);
                    break;
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i=0; i<DIMENSIONS; i++) {
                if (i>0)
                    sb.append(",");
                sb.append(features[i]);
            }
            sb.append(") ");
            sb.append(pointType);
            return sb.toString();
        }

        public dbPoint(Point p, double radius, int minPts) {
            super(p.features);
            this.radius = radius;
            int ptsInRadius = 0;
            for (Point q: points) {
                if (getDistance(p,q) <= radius) {
                    ptsInRadius++;
                }
            }
            if (ptsInRadius >= minPts)
                pointType = PointType.CORE;
            else
                pointType = PointType.NOISE;
        }
    }

    private class Cluster {
        private final List<Point> points;

        public Point getPoint(int i) {
            return points.get(i);
        }

        public int size() {
            return points.size();
        }

        public void addPoint(Point point) {
            points.add(point);
        }

        public boolean removePoint(Point point) {
            return points.remove(point);
        }

        public boolean contains(Point point) {
            return points.contains(point);
        }

        public Point getAverage() {
            double[] means = new double[DIMENSIONS];
            for(int i = 0; i < DIMENSIONS; i++) {
                means[i] = 0.0;
                for (Point point: points)
                    means[i] += point.getFeature(i);
                means[i] = means[i]/points.size();
            }
            return new Point(means);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Point point: points) {
                sb.append(point);
                sb.append("\n");
            }
            return sb.toString();
        }

        public Cluster(List<Point> points) {
            this.points = points;
        }

        public Cluster(Point point) {
            points = new LinkedList<>();
            points.add(point);
        }

        public Cluster() {
            points = new LinkedList<>();
        }
    }

    public Dataset(int DIMENSIONS) {
        this.DIMENSIONS = DIMENSIONS;
    }

    public void newPoint(double[] features) throws WrongDimensionsException {
        if (features.length == DIMENSIONS) {
            points.add(new Point(features));
        } else {
            throw new WrongDimensionsException();
        }
    }

    private double getDistance(Point p1, Point p2) {
        double squareSum = 0.0;
        for (int i=0; i<DIMENSIONS; i++) {
            squareSum += Math.pow(p1.getFeature(i) - p2.getFeature(i),2);
        }
        return Math.sqrt(squareSum);
    }

    private double getDistance(Cluster c1, Cluster c2, Linkage LINKAGE) {
        if (LINKAGE == Linkage.SINGLE)
            return getSingle(c1,c2);
        if (LINKAGE == Linkage.COMPLETE)
            return getComplete(c1,c2);
        else
            return getGroup(c1,c2);
    }

    private double getGroup(Cluster c1, Cluster c2) { //The average of the distances between all the pairs.
        double distanceTotal = 0.0;
        for (int i = 0; i < c1.size(); i++) {
            for (int j = 0; j < c2.size(); j++) {
                distanceTotal += getDistance(c1.getPoint(i), c2.getPoint(j));
            }
        }
        return distanceTotal/(c1.size()*c2.size());
    }

    private double getComplete(Cluster c1, Cluster c2) {
        double largestDistance = 0;
        double distance;
        for (int i = 0; i < c1.size(); i++) {
            for (int j = 0; j < c2.size(); j++) {
                distance = getDistance(c1.getPoint(i), c2.getPoint(j));
                if (distance > largestDistance) {
                    largestDistance = distance;
                }
            }
        }
        return largestDistance;
    }

    private double getSingle(Cluster c1, Cluster c2) {
        double smallestDistance = Integer.MAX_VALUE;
        double distance;
        for (int i = 0; i < c1.size(); i++) {
            for (int j = 0; j < c2.size(); j++) {
                distance = getDistance(c1.getPoint(i), c2.getPoint(j));
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                }
            }
        }
        return smallestDistance;
    }

    private void printClusters(int iteration,List<Cluster> clusters) {
        System.out.println("Iteration "+iteration+":");
        int i=0;
        for (Cluster cluster: clusters) {
            System.out.println(i+++". "+cluster.toString());
        }
        System.out.println();
    }

    public void hierarchicalClustering(Linkage LINKAGE) {
        List<Cluster> clusters = new LinkedList<>();
        for (int i=0; i<points.size(); i++) {
            clusters.add(new Cluster(points.get(i)));
        }
        int iteration = 0;
        printClusters(iteration,clusters);
        while(clusters.size() > 1) {
            int smallI = 0;
            int smallJ = 1;
            double smallestDistance = Double.MAX_VALUE;
            double distance;
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    distance = getDistance(clusters.get(i), clusters.get(j), LINKAGE);
                    if (distance < smallestDistance) {
                        smallestDistance = distance;
                        smallI = i;
                        smallJ = j;
                    }
                }
            }
            Cluster clusterA = clusters.get(smallI);
            Cluster clusterB = clusters.get(smallJ);
            LinkedList<Point> newPoints = new LinkedList<>();
            for (int i = 0; i < clusterA.size(); i++) {
                newPoints.add(clusterA.getPoint(i));
            }
            for (int i = 0; i < clusterB.size(); i++) {
                newPoints.add(clusterB.getPoint(i));
            }
            Cluster newCluster = new Cluster(newPoints);
            clusters.set(smallI, newCluster);
            clusters.remove(smallJ);
            iteration++;
            System.out.println("Merged clusters "+smallI+" and "+smallJ+" with linkage: "+smallestDistance);
            printClusters(iteration,clusters);
        }
    }

    private boolean updateCluster(Point p, int i, Cluster[] clusters) {
        if (clusters[i].contains(p)) {
            return false;
        } else {
            int j = 0;
            while(!clusters[j].removePoint(p)) {j++;}
            clusters[i].addPoint(p);
            return true;
        }
    }

    private void printClusters(int iteration,Cluster[] clusters, Point[] centroids) {
        System.out.println("Iteration "+iteration+":");
        for (int i=0; i < clusters.length; i++) {
            System.out.println(centroids[i]+": "+clusters[i]);
        }
        System.out.println();
    }

    private double withinClusterVariance(Cluster[] clusters, Point[] centroids) {
        double sum = 0;
        for (int j=0; j < clusters.length; j++) {
            for (int i=0; i < clusters[j].size(); i++) {
                sum += Math.pow(getDistance(clusters[j].getPoint(i),centroids[j]),2);
            }
        }
        return sum;
    }

    public void KMeans(double[][] centroidsDouble) throws WrongDimensionsException {
        Point[] centroids = new Point[centroidsDouble.length];
        Cluster[] clusters = new Cluster[centroidsDouble.length];
        for(int i=0; i<centroidsDouble.length; i++) {
            if (centroidsDouble[i].length != DIMENSIONS)
                throw new WrongDimensionsException();
            centroids[i] = new Point(centroidsDouble[i]);
            clusters[i] = new Cluster();
        }
        boolean changed = true;
        boolean first = true;
        int iteration = 0;
        System.out.println("Original centroids:");
        for (Point centroid: centroids) {
            System.out.println(centroid);
        }
        System.out.println();
        while (changed) {
            if(!first)
                changed = false;
            for (Point point: points) {
                double smallestDistance = Double.MAX_VALUE;
                int smallestI = 0;
                double distance;
                for (int i = 0; i < centroids.length; i++) {
                    distance = getDistance(point,centroids[i]);
                    if (distance < smallestDistance) {
                        smallestDistance = distance;
                        smallestI = i;
                    }
                }
                if (first)
                    clusters[smallestI].addPoint(point);
                else if(changed)
                    updateCluster(point, smallestI, clusters);
                else
                    changed = updateCluster(point, smallestI, clusters);
            }
            first = false;
            for (int i = 0; i<clusters.length; i++) {
                centroids[i] = clusters[i].getAverage();
            }
            printClusters(iteration++,clusters,centroids);
        }
        System.out.println("Within-cluster variance: "+withinClusterVariance(clusters,centroids));
    }

    private void printClusters(List<Cluster> clusters) {
        int i=0;
        for (Cluster cluster: clusters) {
            System.out.println(i+++". "+cluster.toString());
        }
        System.out.println();
    }

    public void DBSCAN(double radius, int minPts) {
        dbPoint[] dbPoints = new dbPoint[points.size()];
        List<dbPoint> corePoints = new LinkedList<>();
        for (int i=0; i<dbPoints.length; i++) {
            dbPoints[i] = new dbPoint(points.get(i), radius, minPts);
            if (dbPoints[i].getPointType() == PointType.CORE)
                corePoints.add(dbPoints[i]);
        }
        for (dbPoint p: dbPoints)
            if (p.pointType == PointType.NOISE) {
                p.setBorderPoint(dbPoints);
            }
        List<Cluster> clusters = new LinkedList<>();
        for (dbPoint p: corePoints) {
            if (!p.isInCluster()) {
                Cluster cluster = new Cluster();
                p.addToCluster(cluster, corePoints);
                clusters.add(cluster);
            }
        }
        for (dbPoint p: dbPoints) {
            if (p.getPointType() == PointType.BORDER) {
                p.joinCluster(dbPoints,clusters);
            }
        }
        System.out.println("Noise points:");
        for (dbPoint p: dbPoints) {
            if (p.getPointType() == PointType.NOISE)
                System.out.println(p);
        }
        System.out.println();

        printClusters(clusters);

    }
}

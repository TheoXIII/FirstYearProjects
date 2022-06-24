public class Main {
    public static void main(String[] args) throws WrongDimensionsException {
        /*Dataset data = new Dataset(2);
        data.newPoint(new double[]{37,2});
        data.newPoint(new double[]{65,3});
        data.newPoint(new double[]{34,2});
        data.newPoint(new double[]{38,5});
        data.newPoint(new double[]{38,6});
        data.newPoint(new double[]{38,3});
        data.hierarchicalClustering(Linkage.GROUP);*/

        Dataset data = new Dataset(2);
        data.newPoint(new double[]{3,1});
        data.newPoint(new double[]{5,1});
        data.newPoint(new double[]{4,2});
        data.newPoint(new double[]{5,2});
        data.newPoint(new double[]{2,3});
        data.newPoint(new double[]{7,4});
        data.newPoint(new double[]{1,0});
        data.newPoint(new double[]{8,0});
        data.KMeans(new double[][]{new double[]{1,0},new double[]{4,0}});

        /*Dataset data = new Dataset(2);
        data.newPoint(new double[]{3,1});
        data.newPoint(new double[]{5,1});
        data.newPoint(new double[]{4,2});
        data.newPoint(new double[]{5,2});
        data.newPoint(new double[]{2,5});
        data.newPoint(new double[]{7,4});
        data.newPoint(new double[]{1,0});
        data.newPoint(new double[]{8,0});
        data.KMeans(new double[][]{new double[]{3,4},new double[]{6,4}});*/

        /*Dataset data = new Dataset(2);
        data.newPoint(new double[]{0.325, -0.595});
        data.newPoint(new double[]{0.507,  1.619});
        data.newPoint(new double[]{0.817,  1.895});
        data.newPoint(new double[]{1.147,  0.764});
        data.newPoint(new double[]{-1.285, -0.95});
        data.newPoint(new double[]{-1.237, -0.532});
        data.newPoint(new double[]{1.108,  1.248});
        data.newPoint(new double[]{-0.847, -0.722});
        data.newPoint(new double[]{0.124, -1.346});
        data.newPoint(new double[]{0.910, -0.227});
        data.newPoint(new double[]{0.310, -0.756});
        data.newPoint(new double[]{-1.384, -0.715});
        data.newPoint(new double[]{0.736,  1.15});
        data.newPoint(new double[]{0.511, -0.517});
        data.newPoint(new double[]{-1.081, -0.91});
        data.newPoint(new double[]{0.416,  1.252});
        data.newPoint(new double[]{-2.349, -0.42});
        data.newPoint(new double[]{-0.559, -1.161});
        data.newPoint(new double[]{0.806,  1.054});
        data.newPoint(new double[]{1.023, -0.133});
        data.DBSCAN(0.5,4);*/
    }
}

public record Classification<E>(
        E data,
        String group
) {
    public Classification(E data, String group) {
        this.data = data;
        this.group = group;
    }
}

package model.factory;

public class CreationContext {
    private final int x;
    private final int y;
    private final java.util.Map<String, Object> parameters;

    private CreationContext(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.parameters = new java.util.HashMap<>(builder.parameters);
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public <T> T getParameter(String key, T defaultValue) {
        return (T) parameters.getOrDefault(key, defaultValue);
    }

    public static Builder builder(int x, int y) {
        return new Builder(x, y);
    }

    public static class Builder {
        private final int x;
        private final int y;
        private final java.util.Map<String, Object> parameters = new java.util.HashMap<>();

        private Builder(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Builder withParameter(String key, Object value) {
            parameters.put(key, value);
            return this;
        }

        public Builder withKeyId(int keyId) {
            return withParameter("keyId", keyId);
        }

        public Builder withLockId(int lockId) {
            return withParameter("lockId", lockId);
        }

        public CreationContext build() {
            return new CreationContext(this);
        }
    }
}
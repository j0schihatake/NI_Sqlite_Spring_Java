package com.j0schi.server.vot.generation;

public class Vector3 {
    public float x, y, z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Методы для сложения, вычитания и умножения на скаляр
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Метод для получения длины вектора
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    // Метод для нормализации вектора
    public Vector3 normalize() {
        float length = magnitude();
        return new Vector3(x / length, y / length, z / length);
    }

    // Метод для вычисления расстояния между двумя векторами
    public static float distance(Vector3 a, Vector3 b) {
        return a.subtract(b).magnitude();
    }

    // Метод для преобразования в Vector3Int
    public Vector3Int toVector3Int() {
        return new Vector3Int(Math.round(x), Math.round(y), Math.round(z));
    }

    @Override
    public String toString() {
        return "Vector3(" + x + ", " + y + ", " + z + ")";
    }

    // Сравнение
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector3 vector = (Vector3) obj;
        return Float.compare(vector.x, x) == 0 &&
                Float.compare(vector.y, y) == 0 &&
                Float.compare(vector.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(x) ^ Float.hashCode(y) ^ Float.hashCode(z);
    }
}
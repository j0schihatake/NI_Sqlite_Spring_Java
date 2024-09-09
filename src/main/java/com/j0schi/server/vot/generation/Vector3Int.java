package com.j0schi.server.vot.generation;

import lombok.Data;

@Data
public class Vector3Int {
    public int x, y, z;

    public Vector3Int() {
        this(0, 0, 0);
    }

    public Vector3Int(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Метод для вычисления евклидова расстояния между двумя точками
    public float distance(Vector3Int other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        int dz = this.z - other.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // Методы для сложения, вычитания и умножения на скаляр
    public Vector3Int add(Vector3Int other) {
        return new Vector3Int(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3Int subtract(Vector3Int other) {
        return new Vector3Int(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3Int multiply(int scalar) {
        return new Vector3Int(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Метод для преобразования в Vector3
    public Vector3 toVector3() {
        return new Vector3(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "Vector3Int(" + x + ", " + y + ", " + z + ")";
    }

    // Статические методы для направлений
    public static Vector3Int forward() {
        return new Vector3Int(0, 0, 1);
    }

    public static Vector3Int back() {
        return new Vector3Int(0, 0, -1);
    }

    public static Vector3Int left() {
        return new Vector3Int(-1, 0, 0);
    }

    public static Vector3Int right() {
        return new Vector3Int(1, 0, 0);
    }

    public static Vector3Int up() {
        return new Vector3Int(0, 1, 0);
    }

    public static Vector3Int down() {
        return new Vector3Int(0, -1, 0);
    }

    // Сравнение
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector3Int vector = (Vector3Int) obj;
        return x == vector.x &&
                y == vector.y &&
                z == vector.z;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) ^ Integer.hashCode(y) ^ Integer.hashCode(z);
    }
}

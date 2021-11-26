package com.github.borsch.simplecsv;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private final String firstName;
    private final String lastName;
    private final int age;
    private final float weight;
    private final LocalDateTime lastLoginTime;

    public User(final String firstName, final String lastName, final int age, final float weight, final LocalDateTime lastLoginTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
        this.lastLoginTime = lastLoginTime;
    }

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.weight = builder.weight;
        this.lastLoginTime = builder.lastLoginTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public float getWeight() {
        return weight;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private int age;
        private float weight;
        private LocalDateTime lastLoginTime;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder weight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder lastLoginTime(LocalDateTime lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final User user = (User) o;
        return age == user.age && Float.compare(user.weight, weight) == 0 && Objects.equals(firstName, user.firstName)
            && Objects.equals(lastName, user.lastName) && Objects.equals(lastLoginTime, user.lastLoginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, weight, lastLoginTime);
    }

    @Override
    public String toString() {
        return "User{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", age=" + age +
            ", weight=" + weight +
            ", lastLoginTime=" + lastLoginTime +
            '}';
    }
}

package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.config.HibernateConfig;
import org.example.entity.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class CarDAO {
    private final HibernateConfig hibernateConfig;
    List<Car> cars = new ArrayList<>();

    public List<Car> getAllCars() throws SQLException {
        try (Statement statement = hibernateConfig.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM car_shop");

            while (resultSet.next()) {
                Car car = Car.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .model(resultSet.getString("model"))
                        .price(resultSet.getBigDecimal("price"))
                        .photoUrl(resultSet.getString("photoUrl"))
                        .build();
                cars.add(car);
            }
        }
        return cars;
    }

    public void addCars(Car car) throws SQLException {
        String sql = "INSERT INTO car_shop (name, model,price,photoUrl) VALUES (?,?,?,?)";
        try (PreparedStatement statement = hibernateConfig.getConnection().prepareStatement(sql)) {
            statement.setString(1, car.getName());
            statement.setString(2, car.getModel());
            statement.setBigDecimal(3, car.getPrice());
            statement.setString(4, car.getPhotoUrl());
            statement.executeUpdate();
        }
    }
}
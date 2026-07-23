package com.example.routeservice.repository;

import com.example.routeservice.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity,Long>

{
    List<RouteEntity> findByStartLocationIgnoreCaseAndEndLocationIgnoreCase(String start, String end);
}
/*2. How Repository Methods Work in Spring Data JPA?
You are completely right that for basic operations (like findById or save),
we don't have to write any methods because Spring Boot provides them automatically through JpaRepository.
However, you must manually add a method to your repository interface if you want to filter by specific fields (like StartLocation and EndLocation).
Why it's failing right now:Spring Data JPA uses Query Methods.
It looks at the name of the method and automatically writes the SQL query for you behind the scenes.
But it can only do this if you declare it in your interface first.If you see that red error line,
it means you typed the method name in your service file, but you forgot to declare it in your interface file.*/
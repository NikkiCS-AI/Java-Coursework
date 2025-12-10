# Drink Serving Robotic System — Java Coursework Project

This repository contains a group Java project developed for a coursework assignment. The task was to design and implement a Drink Serving Robotic System for a large, crowded restaurant in Malaysia.
The system focuses on managing congestion, ensuring safe social distancing, and simulating how a robot navigates and serves drinks across five restaurant locations.

The project demonstrates applied knowledge of object-oriented programming, user interaction, control statements, data structures, and basic simulation logic in Java.

## Project Overview

The system is designed for a fictional restaurant with these restricted spots:

* Dining Foyer

- Main Dining Hall

- Dining Room One

- Dining Room Two

- Family Dining Room

Each area has its own spot ID, spot name, area size, maximum capacity, and average robot serving time.
The program ensures that the robot follows social distancing guidelines (3 ft / 1 m) and safely moves within crowded areas.

## Features Implemented
1. Static Drink Serving (Entrance Permission System)

- Determines whether the robot is allowed to enter a specific dining area based on:

- Current number of people/robots (generated using RNG)

- Maximum capacity of the spot

- User-input available space

- Social distancing constraints

- If the spot is full, the system offers waiting time or allows choosing another spot.

2. Dynamic Drink Serving (Distance & Safety Check)

- Once inside a spot, the robot must maintain safe dynamic distancing. The program:

- Asks the user for distances from Left, Right, Front, and Back

- Verifies whether distances meet the minimum guideline

- Advises the robot to move away by 0.5 m or 1 m if too close

- Determines the robot’s contact status (safe / casual contact)

- Logs:

  - Robot ID (student ID)

  - Robot Name (student’s name)

  - Spot ID

  - Spot Name

  - Date & Time

  - Contact Status

3. Java Concepts Demonstrated

The system uses a wide range of Java features, including:

- Classes, Objects, Encapsulation

- Constructors & Instance Variables

- Inheritance & Polymorphism

- Method Overloading

- Arrays & Data Structures

- Switch, If-Else, Nested Loops, While Loops

- Random Number Generation (RNG)

- User Input Handling

- Separation of Static vs Dynamic Distance Logic

##  How the System Works

- User selects a restaurant spot

- Program checks capacity & spacing requirements

- Robot either enters or is denied access

- If access is granted, user inputs dynamic distances

- Program evaluates safety and suggests adjustments

- Robot contact status is printed and logged

## What I Learned

Throughout this project I strengthened my understanding of object-oriented design by structuring the game into clear, modular components such as rendering, state management, input handling, and audio. I also gained experience working with JavaFX, especially in managing UI scenes, bindings, and custom components. Designing game loops, handling real-time updates, and synchronising logic with graphical rendering taught me how to build interactive systems that remain responsive and maintainable. Additionally, implementing features such as pause and game-over states, score tracking, row-clearing effects, and the hold mechanic helped me think more carefully about game architecture, event flow, and clean separation of responsibilities.

##  Notes

This system simulates real-life constraints in a simplified Java environment.

Assumptions made (spot sizes, safe distances, etc.) are documented in the report.

The project demonstrates a practical application of Java fundamentals in a safety-focused robotics scenario.

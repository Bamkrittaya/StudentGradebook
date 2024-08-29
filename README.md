# StudentGradebook

This repository contains a simple project from Object-Oriented Programming (OOP) to create a StudentGradebook application in Java from scratch. The project demonstrates key OOP principles such as encapsulation, inheritance, and polymorphism while providing a functional grade book system for managing student records.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Introduction
The StudentGradebook project is designed to help users manage and track student scores across multiple subjects. It offers a simple GUI for inputting student names and scores, calculating averages, and storing the data persistently. This project is an excellent example of applying OOP principles in a real-world scenario, making it a useful learning tool for students and educators alike.

## Features
- **GUI Interface**: Uses Java Swing to provide a user-friendly graphical interface for entering and viewing student data.
- **Score Validation**: Ensures that scores are valid and fall within the acceptable range (0-100).
- **Data Persistence**: Saves student names and their corresponding GPAs to a file (`grades.txt`) for future reference.
- **Error Handling**: Provides mechanisms to handle incorrect inputs and allows users to correct mistakes.
- **Customizable Subjects**: The current version supports five subjects (Maths, Science, English, Economy, History), but the structure allows easy modification for additional subjects.

## Installation
To run this project on your local machine, follow these steps:

1. **Clone the repository**:
   ```sh
   git clone https://github.com/Bamkrittaya/StudentGradebook.git
   ```
2. **Navigate to the project directory**:
   ```sh
   cd StudentGradebook
   ```
3. **Compile the Java files**:
   ```sh
   javac StudentScores.java
   ```
4. **Run the application**:
   ```sh
   java StudentScores
   ```

## Usage
Upon running the application, you'll be presented with a GUI where you can:

- Enter the name of the student and their scores for each subject.
- The application will calculate and display the average score (GPA) for each student.
- You can add multiple students, correct any mistakes, and save the data to a file.

## Project Structure
```
StudentGradebook/
│
├── StudentScores.java      # Main class with the GUI and logic for managing student scores
├── grades.txt              # Output file for storing student names and GPAs
└── README.md               # Project documentation (this file)
```

## Contributing
Contributions are welcome! If you have suggestions or improvements, please follow these steps:

1. **Fork the repository**
2. **Create a new branch** (`git checkout -b feature/YourFeatureName`)
3. **Commit your changes** (`git commit -m 'Add some feature'`)
4. **Push to the branch** (`git push origin feature/YourFeatureName`)
5. **Create a Pull Request**

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

# SpringWeather-app

SpringWeather-app is a modern weather application built with Spring Boot. It provides real-time weather information for cities worldwide, featuring a robust backend and a clear API interface. This project demonstrates best practices in Spring Boot development with a focus on RESTful APIs, configuration, and testability.

## Features

- Get current weather for any city
- RESTful API endpoints
- Integration with external weather data providers (e.g., OpenWeatherMap API)
- Error handling and validation
- Easy configuration for API keys and other settings

## Getting Started

### Prerequisites

- Java 17 or above
- Maven 3.6+ (or Gradle, if configured)

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yashutoxie/SpringWeather-app.git
   cd SpringWeather-app
   ```

2. **Configure API Keys:**
   - Copy `src/main/resources/application.properties.example` to `application.properties`.
   - Insert your weather API key and any other config values.

3. **Build and Run:**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or using Maven:
   ```bash
   mvn spring-boot:run
   ```
   The app will start on [http://localhost:8080](http://localhost:8080).

## Usage

- Access the API at `/api/weather?city=CityName`
- Example:
  ```
  GET http://localhost:8080/api/weather?city=London
  ```

---

> **ProTip:**  
> You can also quickly check the weather for any location using the REST endpoint:  
> ```
> GET /api/v1/weather/{location}
> ```
> Example:  
> ```
> GET http://localhost:8080/api/v1/weather/NewYork
> ```

---

## Project Structure

*Note: This is a partial structure. [View all files on GitHub.](https://github.com/yashutoxie/SpringWeather-app)*

```
SpringWeather-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ... (Java packages, e.g., domain, entity, providers, resource, service, transformer)
│   │   └── resources/
│   │       ├── application.properties.example
│   │       └── (other configs/resources)
│   └── test/
│       └── java/
│           └── ... (test classes)
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

### Why Use This Project File Structure?

The structure of this project follows best practices for Spring Boot applications, making the code base organized and maintainable:

- **domain/**: Contains domain models that define the core business logic and entities.
- **entity/**: Houses JPA entities that map to database tables, ensuring a clear separation between persistence and business logic.
- **providers/**: Encapsulates integration with external weather data providers, making it easy to swap or update APIs.
- **resource/**: Holds REST controllers and API endpoints, keeping web concerns isolated.
- **service/**: Contains service layer classes which implement the application's core functionality and business rules.
- **transformer/**: Responsible for converting data between different layers and formats (e.g., DTOs and entities).

**Benefits:**
- Improves readability and maintainability by enforcing separation of concerns.
- Makes it easier to onboard new collaborators by following a standard structure.
- Supports scalable development as new features or integrations can be added without cluttering the codebase.
- Facilitates easier testing and debugging by keeping related code together.

## Improvements Over Existing Projects

- Modular provider integration: Easily add or replace external weather data sources.
- Clear separation between entity, domain, and service logic.
- Enhanced error handling and validation for robust API responses.
- Designed with extensibility in mind—ready for new features or endpoints.
- Clean and concise project structure for faster development and onboarding.

## Collaboration Appreciated!

This project is open to ideas and improvements from the community!  
If you have suggestions, want to report bugs, or wish to add new features, you’re welcome to participate.

- **Fork the repository** and start experimenting.
- **Open issues** to discuss bugs or new features.
- **Submit pull requests** with your improvements.
- All kinds of collaboration and feedback are appreciated—let’s make this project even better together!

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [OpenWeatherMap API](https://openweathermap.org/api) (or other weather providers)
- Thanks to everyone contributing ideas and improvements!

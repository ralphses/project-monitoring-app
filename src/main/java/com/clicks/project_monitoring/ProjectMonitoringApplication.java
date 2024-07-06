package com.clicks.project_monitoring;

import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.model.Project;
import com.clicks.project_monitoring.model.user.ProjectRepository;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.Supervisor;
import com.clicks.project_monitoring.model.user.User;
import com.clicks.project_monitoring.repositories.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ProjectMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMonitoringApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			Faker faker,
			ProjectRepository projectRepository) {
		return args -> {

			// Seed users
			User adminUser = User.builder()
					.name(faker.name().fullName())
					.password("password")
					.email(faker.internet().emailAddress())
					.role(UserRole.ADMIN)
					.reference(UUID.randomUUID().toString())
					.username("admin")
					.comments(new ArrayList<>())
					.build();

			String student1Ref = UUID.randomUUID().toString();

			Student studentUser = new Student();
			studentUser.setUsername("student");
			studentUser.setPassword("password");
			studentUser.setEmail(faker.internet().emailAddress());
			studentUser.setRole(UserRole.STUDENT);
			studentUser.setReference(student1Ref);
			studentUser.setComments(new ArrayList<>());

			Project project = Project.builder()
					.description(faker.lorem().sentence(25))
					.title(faker.lorem().sentence(5))
					.status(EntityStatus.INITIATED)
					.userReference(student1Ref)
					.tasks(new ArrayList<>())
					.progressReports(new ArrayList<>())
					.reference(UUID.randomUUID().toString())
					.build();

			projectRepository.save(project);

			studentUser.setProject(project);

			Student studentUser2 = new Student();
			studentUser.setUsername("student2");
			studentUser.setPassword("password");
			studentUser.setEmail(faker.internet().emailAddress());
			studentUser.setRole(UserRole.STUDENT);
			studentUser.setReference(UUID.randomUUID().toString());
			studentUser.setComments(new ArrayList<>());

			Supervisor supervisorUser = new Supervisor();
			supervisorUser.setName(faker.name().fullName());
			supervisorUser.setPassword("password");
			supervisorUser.setEmail(faker.internet().emailAddress());
			supervisorUser.setRole(UserRole.SUPERVISOR);
			supervisorUser.setReference(UUID.randomUUID().toString());
			supervisorUser.setUsername("supervisor");
			supervisorUser.setComments(new ArrayList<>());
			supervisorUser.setStudents(Arrays.asList(studentUser, studentUser2));

			userRepository.saveAll(List.of(adminUser, studentUser, studentUser2, supervisorUser));
		};
	}
}

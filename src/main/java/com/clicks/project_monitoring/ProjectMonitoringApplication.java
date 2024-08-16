package com.clicks.project_monitoring;

import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.model.*;
import com.clicks.project_monitoring.model.user.Admin;
import com.clicks.project_monitoring.model.user.SecuredUser;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.Supervisor;
import com.clicks.project_monitoring.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
			ProjectRepository projectRepository,
			ProgressReportRepository progressReportRepository,
			TaskRepository taskRepository,
			StudentRepository studentRepository,
			SupervisorRepository supervisorRepository,
			AdminRepository adminRepository,
			ProgressReportStageRepository progressReportStageRepository,
			CommentRepository commentRepository) {
		return args -> {

			// Seed users
			SecuredUser studentSecuredUser = userRepository.save(
					new SecuredUser(
							"student",
							"password",
							UserRole.STUDENT));

			SecuredUser adminSecuredUser = userRepository.save(
					new SecuredUser(
							"admin",
							"password",
							UserRole.ADMIN));

			SecuredUser supervisorSecuredUser = userRepository.save(
					new SecuredUser(
							"supervisor",
							"password",
							UserRole.SUPERVISOR));


			Student studentUser = new Student("student name", studentSecuredUser.getReference());


			Task task = Task.builder()
					.description(faker.lorem().sentence(20))
					.title(faker.lorem().sentence(5))
					.createdAt(LocalDateTime.now())
					.expectedDeliveryDate(LocalDateTime.now().plusDays(1))
					.status(EntityStatus.INITIATED)
					.reference(UUID.randomUUID().toString())
					.comments(new ArrayList<>())
					.build();

			Task task2 = Task.builder()
					.description(faker.lorem().sentence(20))
					.title(faker.lorem().sentence(5))
					.createdAt(LocalDateTime.now())
					.expectedDeliveryDate(LocalDateTime.now().plusDays(1))
					.status(EntityStatus.INITIATED)
					.reference(UUID.randomUUID().toString())
					.comments(new ArrayList<>())
					.build();

			List<Task> savedTasks = taskRepository.saveAll(List.of(task, task2));

			ProgressReportStage stage1 = ProgressReportStage.builder()
					.name("Chapter 1")
					.tasks(new ArrayList<>(List.of(savedTasks.get(0))))
					.reference(UUID.randomUUID().toString())
					.build();

			ProgressReportStage stage2 = ProgressReportStage.builder()
					.name("Chapter 2")
					.tasks(new ArrayList<>(List.of(savedTasks.get(1))))
					.reference(UUID.randomUUID().toString())
					.build();
			List<ProgressReportStage> progressReportStages = progressReportStageRepository.saveAll(List.of(stage1, stage2));

			ProgressReport progressReport = ProgressReport.builder()
					.stages(progressReportStages)
					.build();

			ProgressReport savedReport = progressReportRepository.save(progressReport);

			Project project = Project.builder()
					.description(faker.lorem().sentence(25))
					.title(faker.lorem().sentence(5))
					.status(EntityStatus.INITIATED)
					.userReference(studentUser.getUserId())
					.progressReport(savedReport)
					.reference(UUID.randomUUID().toString())
					.build();


			studentRepository.save(studentUser);

			Supervisor supervisorUser = new Supervisor("supervisor name", supervisorSecuredUser.getReference());
			supervisorUser.getStudents().add(studentUser);

			supervisorRepository.save(supervisorUser);

			Admin adminUser = new Admin("admin name", adminSecuredUser.getReference());
			adminRepository.save(adminUser);


			studentUser.setSupervisor(supervisorUser.getName());

			projectRepository.save(project);

			Comment taskComment = Comment.builder()
					.content(faker.lorem().sentence(26))
					.createdAt(LocalDateTime.now())
					.userReference(supervisorUser.getUserId())
					.reference(UUID.randomUUID().toString())
					.build();

			Comment saved = commentRepository.save(taskComment);
			task2.getComments().add(saved);



			Comment comment = Comment.builder()
					.content(faker.lorem().sentence(26))
					.createdAt(LocalDateTime.now())
					.userReference(supervisorUser.getUserId())
					.reference(UUID.randomUUID().toString())
					.build();

			Comment comment2 = Comment.builder()
					.content(faker.lorem().sentence(26))
					.createdAt(LocalDateTime.now())
					.userReference(supervisorUser.getUserId())
					.reference(UUID.randomUUID().toString())
					.build();

			commentRepository.saveAll(List.of(comment, comment2));
			task2.getComments().addAll(commentRepository.saveAll(List.of(comment, comment2)));

			taskRepository.saveAll(List.of(task, task2));
//
//
//			projectRepository.save(project);
//
//
//			userRepository.save(studentUser);
//
//			userRepository.saveAll(List.of(adminUser, supervisorUser));

		};
	}
}

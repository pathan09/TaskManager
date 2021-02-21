# DROP TABLE IF EXISTS `task_manager.roles`;
# DROP TABLE IF EXISTS `task_manager.users`;
# DROP TABLE IF EXISTS `task_manager.user_role`;

INSERT INTO `roles` (`id`, `role`) VALUES (1, "ADMIN");
INSERT INTO `roles` (`id`, `role`) VALUES (2, "USER");

INSERT INTO `users` (`id`, `password`, `user_name`, `active`)
VALUES (1, "$2a$10$7qyuAq0bzMQ9pFRNBwemrecQCSEogw53mGeBiONhbuvnjiUs2piMW", "user_one", 1);

INSERT INTO `users` (`id`, `password`, `user_name`, `active`)
VALUES (2, "$2a$10$7qyuAq0bzMQ9pFRNBwemrecQCSEogw53mGeBiONhbuvnjiUs2piMW", "admin", 1);

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 2);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (2, 1);

INSERT INTO `projects`(`id`, `created_at`, `name`, `user_name`)
VALUES (1,'2021-02-13', 'Todo Management', 'user_one');

INSERT INTO `projects`(`id`, `created_at`, `name`, `user_name`)
VALUES (2,'2021-02-13', 'Project manager', 'admin');

INSERT INTO `tasks`(`id`, `created_at`, `description`, `due_date`, `status`, `user_name`, `project_id`)
VALUES (1, '2021-02-13', 'Define project structure', '2021-02-15', 'OPEN', 'user_one', 1);
INSERT INTO `tasks`(`id`, `created_at`, `description`, `due_date`, `status`, `user_name`, `project_id`)
VALUES (2, '2021-02-13', 'Design database', '2021-02-15', 'OPEN', 'user_one', 1);

INSERT INTO `tasks`(`id`, `created_at`, `description`, `due_date`, `status`, `user_name`, `project_id`)
VALUES (3, '2021-02-13', 'Implement Spring Boot', '2021-02-15', 'OPEN', 'admin', 2);
INSERT INTO `tasks`(`id`, `created_at`, `description`, `due_date`, `status`, `user_name`, `project_id`)
VALUES (4, '2021-02-13', 'Complete development', '2021-02-15', 'OPEN', 'admin', 2);
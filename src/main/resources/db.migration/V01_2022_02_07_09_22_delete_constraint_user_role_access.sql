select constraint_name from information_schema.constraint_column_usage
where table_name='user_role_access' and column_name='role_access_id'
  and constraint_name <> 'unique_access_user';

alter table user_role_access drop constraint "uk_6tg9le1bxknkw4v36s07709ia";
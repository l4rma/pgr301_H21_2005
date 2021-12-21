resource "aws_ecr_repository" "student_repo" {
  name = var.ecr_repo_name 
}

resource "aws_ecr_repository" "student_repo_sensor" {
  name = "2005-sensur-repo"
}



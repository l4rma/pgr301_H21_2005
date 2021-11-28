variable "aws_region" {
  description = "AWS region"
  type = string
  default = "eu-west-1"
}

variable "ecr_repo_name" {
  description = "Name of ECR repository"
  type = string
}

variable "aws_region" {
  description = "AWS region"
  type = string
  default = "eu-west-1"
}

variable "bucket_name" {
  description = "Name of the bucket"
  type = string
}

variable "bucket_key" {
  description = "The bucket path"
  type = string
}

variable "ecr_repo_name" {
  description = "Name of ECR repository"
  type = string
}

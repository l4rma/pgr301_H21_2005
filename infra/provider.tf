terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "3.56.0"
    }
  }
  backend "s3" {
    bucket = var.bucket_name
	key = var.bucket_key
    region = var.aws_region
  }
}
provider "aws" {
  region = var.aws_region
}


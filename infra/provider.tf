terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "3.56.0"
    }
  }
  backend "s3" {
    bucket = "pgr301-lama003-terraform"
    key = "terraform.state"
    region = "eu-west-1"
  }
}
provider "aws" {
  region = "eu-west-1"
}

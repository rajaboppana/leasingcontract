## Usage

1. Find `Dockerfile` and `docker-leasing-schema.sql` files located in the project
   directory.

2. Build the Docker image using the following command:

   ```shell
   docker build -t leasing_db .

3. After successful build execute the following command:

   ```shell
   docker run -p 3306:3306 -d leasing_db

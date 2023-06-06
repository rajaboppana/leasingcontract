## Getting Started

1. Find `Dockerfile` and `docker-leasing-schema.sql` files located in the project
   directory.

2. Build the Docker image using the following command:

   ```shell
   docker build -t leasing_db .

3. After successful build execute the following command:

   ```shell
   docker run -p 3306:3306 -d leasing_db

4. Run `LeasingcontractApplication.java` to run the application.

5. Find `data.sql` to create the initial schema. 

## Implementation

  1. Created the required CRUD end points for all the entities and appropriate 
     mappings.
  2. Test coverage for the APIs and the service implementations.
  3. Created Integration test to test MySQL container (ITLeasingContract)
  4. Created a Transformer class to covert DTO to Entity and vice versa, I used 
     mapper at first, I don't have control.I changed later.
  5. Due to time constraint I could not implement better exception handling and better 
     formatted responses.
    


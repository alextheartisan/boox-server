# boox

## Architecture

- Layered Logic: Controller -> Service -> Repository
  - Controllers are supposed to route the action to Services
  - Controller should be lightweight and simple

## TODO

- DTOs
- Response Wrapper
- Uploading Files 
- Downloading Files 

## REST Best practices

- Specifications
  - [YARAS](https://github.com/darrin/yaras)
- Basic things
  - DTOs
  - Filtering
  - Searching
  - Pagination & Sorting 
  - Versioning?
  - Error handling
  - Documentation (Swagger). Should Spec come first?
  - Authentication/Authorization
  - Contract First (Open API)
    - Endpoint
    - Operations
    - Request Structure
    - Response Structure

- Fancy things
  - HATEOAS?

### Links

- https://codeburst.io/spring-boot-rest-microservices-best-practices-2a6e50797115
- https://dzone.com/articles/rest-api-best-practices-with-design-examples-from

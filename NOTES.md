## LEARNING

- Email address is of the format local-part@domain. local-part size is <= 64, domain 
is <= 255. The maximum length allowed for a reverse-path or forward-path is 256. 
So, max allowed length for an email is 254, including the @. `< + local-part@domain + >`. 
See [RFC 5321](https://datatracker.ietf.org/doc/html/rfc5321#section-4.5.3.1)
- Create a custom PasswordEncoder to make sure it is memory secure. Work with 
conversions of `char[]` to a `byte[]` without using String to make sure its secure.
- Create custom converters for Mongo serialization. Then add these convertes as part of MongoCustomConversions and define as a bean.

## TODO

- [ ] Setup test containers for testing
- [X] User signup
    - [X] Save the account to db
    - [X] Create the home folder entry in the mongo
    - [X] Create a container for the user
    - [X] Rollback if error at any step
    - [X] Generate JWT Token for auth
- [ ] Tracing
    - [X] Support Tracing
    - [ ] Export data to some collector
    - [ ] Check how to start generating the trace ids from the nginx
    - [X] Add trace id in the response.
- [X] Login Flow
    - [X] User can login with email/password
    - [X] Generate a token for user login
    - [X] Return back user metadata once login (similar to signup)
- [ ] Directories:
    - [ ] Send directory hierrachy for the owner id
    - [ ] Create a directory
    - [ ] Put a hard coded check on number of folders at each level.
    - [ ] Put a hard coded check on folder tree depth.

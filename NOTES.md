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
- [ ] User signup
    - [ ] Save the account to db
    - [ ] Create the home folder entry in the mongo
    - [ ] Create a container for the user
    - [ ] Rollback if error at any step
    - [ ] Generate JWT Token for auth

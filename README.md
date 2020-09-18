# Watch Tower

A little Kotlin app that hits AWS's CloudTrail API & prints out all non-read-only events for a given day in a tab delimited format, 
sorted by username.

Example Output
```
Time    Username        Event   Resource
2020-09-18T09:16:08Z    za SwitchRole      []
2020-09-18T09:18:45Z    za AuthorizeSecurityGroupIngress   [Resource(ResourceType=AWS::EC2::SecurityGroup, ResourceName=sg-z4z4zz4z)]
2020-09-18T09:32:18Z    za ExitRole        []
```

## Getting Started

### Requirements
* JDK 8
* AWS IAM Credentials

### Running app
1. Make a copy of the `.env.example` as `.env` and fill in the values as necessary.
1. Run `./script/server` to run the app - it will print to stdout - feel free to capture and output to a file or something.

### Building/testing app
Run `./script/build` to build the application JAR file
Run `./script/test` to run the tests

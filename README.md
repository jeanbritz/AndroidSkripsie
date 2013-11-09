Android mobile applcation
=========================
### SDK version 4.2

## Requirements
- Android version version 4.2 or later.
- Your device is required to have the NFC feature (also known as Android Beam).

## Description

This mobile application is used by the consumer in this project. It mainly interacts with the web server by accessing its REST API
and it also interacts with the Arduino module (Vendor device) in Peer-to-peer mode.

This application allows the user to login using their credentials created with the webserver in DjangoSkripsie.

They can access their Claims, Invoices and Transactions (these options are also available on the web interface of the web server)

When the mobile device interacts with the Vendor device via NFC (set up in Peer-to-peer mode) it sends NDEF messages which then invokes a request on the vendor device

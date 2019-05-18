# HSU

File upload utility to be used together with the multipart-like upload structure in the HTTP server embedded into the JDK.

It weakly references handlers for each part of the upload multipart request. If there is no handler registered for a given part, it is ignored.

The handlers itself must implement the given interface, to be able to handle the upload part. 

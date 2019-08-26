# HSU

HSU is a collection of small utilities for the HTTP server embedded into the JDK. Currently it supports following features:
* Cookies handling (adding and updating values)
* Session handling (based on cookies)
* File upload and handling of multipart messages.

Most of these functionalities are accessible via the `HsuHttpExchange` object, which is basically a wrapper for the object created by the embedded webserver. It automatically creates the cookies and the session handler. There is a rather simplistic query string object representation and also provides access to the original HttpExchange.

## Cookies handling
This feature is a rather simplistic approach of setting and receiving cookies and data stored in them. Currently only session cookies are available, and the values are used for every attribute (http only, path, domain etc.). Nevertheless, it is possible to use these cookies to store and retrieve data.

The main `hsu.http.Cookies` object takes the `com.sun.net.httpserver.HttpExchange` object as a constructor parameter and features two public methods: `getSessionValues()` and `addSessionValue(key, value)`. The `addSessionValue` must be invoked before sending the response code and content length back to the client.

## Sessions (based on cookies)
The session feature heavily builds upon the cookies. It sets a cookie with a fixed name with a value of a pseudo-random session ID. Upon retrieving the session object, there is a `isNew()` method, similar to the session objects created via a servlet. A session ID is always present in the retreived session object.

The main class is the `hsu.http.SessionHandler` class, which takes a `com.sun.net.httpserver.HttpExchange` object as the constructor parameter. It offers only one public method, the `getSession()`, which returns an instance of the `hsu.http.session.Session` object. This session objects features with the methods mentioned above.

## File upload of HTTP multipart messages
A file handler is created by passing an upload handler as a constructor parameter. The handler itself must implement the given interface, to be able to handle the upload part. The default handler will accept every incoming parameter, for which the name is passed when creating the handler instance. The uploaded elements are then accessable via the `getParameters()` method, which will return a copy of the handlers internal `Map`, holding every parameter and `File` instances.

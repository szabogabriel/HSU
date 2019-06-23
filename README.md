# HSU

HSU is a collection of small utilities of the HTTP server embedded into the JDK. Currently it supports following features:
* Cookies handling (adding and updating values)
* Session handling (based on cookies)
* File upload of multipart messages.

## Cookies handling
This feature is a rather simplistic approach of setting and receiving cookies and data stored in them. Currently only session cookies are available, and the values are used for every attribute (http only, path, domain etc.). Nevertheless, it is possible to use these cookies to store and retrieve data.

The main `hsu.http.Cookies` object takes the `com.sun.net.httpserver.HttpExchange` object as a constructor parameter and features two public methods: `getSessionValues()` and `addSessionValue(key, value)`. The `addSessionValue` must be invoked before sending the response code and content length back to the client.

## Sessions (based on cookies)
The session feature heavily builds upon the cookies. It sets a cookie with a fixed name with a value of a pseudo-random session ID. Upon retrieving the session object, there is a `isNew()` method, similar to the session objects created via a servlet. A session ID is always present in the retreived session object.

The main class is the `hsu.http.SessionHandler` class, which takes a `com.sun.net.httpserver.HttpExchange` object as the constructor parameter. It offers only one public method, the `getSession()`, which returns an instance of the `hsu.http.session.Session` object. This session objects features with the methods mentioned above.

## File upload of HTTP multipart messages
It weakly references handlers for each part of the upload multipart request. If there is no handler registered for a given part, it is ignored. The handlers itself must implement the given interface, to be able to handle the upload part. 

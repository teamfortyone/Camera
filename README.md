# Camera

The application is built using Kotlin. It handles the process of capturing the image, uploading it to the backend, and displaying the response from the backend to the user. The user has an option to either capture the image by using the phone camera or uploading an existing image from their image gallery. 

The image is then uploaded to the backend server by sending the image via an HTTP POST request as a multipart form data. The backend server pre-processes the image and puts it through the Machine Learning model, and sends the generated captions as a JSON response. 

The Android client, upon receiving the response, parses the JSON and displays the captions in a List-View. The client is also equipped with a type of assistive technology which is implemented using TTS (Text-to-Speech) which reads the generated caption aloud

## Getting Started

Clone this repositery 

### Requirements
```
Android Studio
kotlin == '1.36.1'
Gradle plugin Version == '3.6.2'
Emulator Android 9.0
```

<img src="https://i.imgur.com/uPkXj8v.jpgg" alt="drawing" width="200"/>   <img src="https://i.imgur.com/n3OUYyR.png" alt="drawing" width="200"/>   <img src="https://i.imgur.com/ElsiRbE.png" alt="drawing" width="200"/>

## Built With

*[Kotlin]([https://kotlinlang.org/](https://kotlinlang.org/))- The language used

*[Retrofit]([https://square.github.io/retrofit/](https://square.github.io/retrofit/))- A type-safe  **HTTP client**  for Android

*[Dexter]([https://github.com/Karumi/Dexter](https://github.com/Karumi/Dexter))- Simplifying  process of asking runtime permisson

*[OkHttp]([https://square.github.io/okhttp/](https://square.github.io/okhttp/)) - Using OkHttp for efficient network access


## Further Scope

*Self driving cars

*Aid to the blind

*CCTV cameras


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

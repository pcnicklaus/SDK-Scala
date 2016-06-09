# SDK-Scala

[logo]: https://github.com/RecastAI/SDK-NodeJs/blob/master/misc/logo-inline.png "Recast.AI"

![alt text][logo]

Recast.AI official SDK in Scala.

## Synospis

This module is a Scala interface to the [Recast.AI](https://recast.ai) API. It allows you to make request to your bots

!! This is still a work in progress - DO NOT USE IT YET !!

## Installation

### Via sbt

Add those lines into your build.sbt file

```scala
resolvers += Resolver.bintrayRepo("recast-ai", "generic")

libraryDependencies ++= Seq(
    "ai.recast" %% "sdk_scala" % "0.7.0"
  )
```

## Usage

### Module

```scala
import recast.client._

object HelloBot {
  def main(args: Array[String]): Unit = {
    val client = new Client("YOUR_TOKEN")

    try {
      val response = client.textRequest("Hello")
      // Do your code
      response.intent() match {
        Some("hello-greetings") => println("Hello, world")
        _ => println("I didn't understand, sorry !")
      }
    } catch {
      // handle error
      case _ => println("Something went wrong...")
    }
  }
}
```

## More

You can view the whole API reference at [man.recast.ai](https://man.recast.ai).

## Author

Jérôme Houdan, jerome.houdan@recast.ai

You can follow us on Twitter at [@recastai](https://twitter.com/recastai) for updates and releases.

## License

Copyright (c) [2016] [Recast.AI](https://recast.ai)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

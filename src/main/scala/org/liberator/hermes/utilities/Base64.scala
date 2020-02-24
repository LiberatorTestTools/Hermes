/*
 * Copyright (c) 2018 Totally Ratted Ltd T/A Totally Ratted Developments
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.liberator.hermes.utilities

import java.nio.ByteBuffer

import sun.misc.{BASE64Decoder, BASE64Encoder}

object Base64 extends App {

  def encodeString(string: String): String = {
    val encoded = new BASE64Encoder()
      .encode(string.getBytes())
      .replace("\\n", "")
      .replace("\\r", "")
    encoded
  }

  def encodeBytes(string: Array[Byte]): String = {
    val encoded = new BASE64Encoder()
      .encode(string)
      .replace("\\n", "")
      .replace("\\r", "")
    encoded
  }

  def decodeString(string: String): ByteBuffer = {
    val decoded = new BASE64Decoder().decodeBufferToByteBuffer(string)
    decoded
  }

}

package utilities

object CallType extends Enumeration {
  type CallType = Value
  val Delete, Get, Head, Option, Patch, Post, Put = Value
}

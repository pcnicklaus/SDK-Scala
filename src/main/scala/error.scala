package recast.error {
  case class RecastError(message: String) extends Exception(message)
}

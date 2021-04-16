package com.github.tanjag.bikeapp.model

case class Error(code: Integer, message: String)

case class RestErrors(errors: List[Error])
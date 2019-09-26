package com.nschirmer.request_api.requestapi.response


/**
 * Response pattern:

Data {
    offset (int, optional): The requested offset (number of skipped results) of the call.,
    limit (int, optional): The requested result limit.,
    total (int, optional): The total number of resources available given the current filter set.,
    count (int, optional): The total number of results returned by this call.,
    results (Array[Character], optional): The list of characters returned by the call.
}

* **/

class ResponseData<T>(val offset: Int, val limit: Int, val results: List<T>)
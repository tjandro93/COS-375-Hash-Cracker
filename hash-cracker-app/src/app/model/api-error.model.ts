/*
Model of ApiError which may be returned from REST calls
 */

export class ApiError {
  constructor(public status?: string,
              public timestamp?: string,
              public message?: string,
              public debugMessage?: string) {}
}

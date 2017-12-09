export class ApiError {
  constructor(public status?: string,
              public timestamp?: string,
              public message?: string,
              public debugMessage?: string) {}
}

import {ApiError} from "./api-error.model";
import {Secret} from "./secret.model";

export class RestResponse {

  constructor(public response?: Secret | Secret[] | ApiError | any) {
  }


  isApiError(): boolean {
    if (this.hasValue()) {
      return this.response == null || this.response instanceof ApiError;
    }
    else {
      return false;
    }
  }

  isSecret(): boolean {
    if (this.hasValue()) {
      return this.response == null || this.response instanceof Secret;
    }
    else {
      return false;
    }
  }

  isSecretArray(): boolean {
    if (this.hasValue()) {
      return this.response instanceof Array && this.response.every(function (element) {
        return element instanceof Secret;
      });

    } else {
      return false;
    }
  }


  hasValue(): boolean {
    return this.response != null && this.response != undefined;
  }

  getSecret(): Secret {
    if (this.response.isSecret()) {
      return this.response;
    }
  }

  getSecrets(): Secret[] {
    if (this.response.isSecretArray()) {
      return this.response;
    }
  }

  getApiError(): ApiError {
    if (this.response.isApiError()) {
      return this.response;
    }
  }

}

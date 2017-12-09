
import {Component} from "@angular/core";
import {Secret} from "../model/secret.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SecretRepository} from "../model/secret.repository";
import {ApiError} from "../model/api-error.model";

/*
Component to be used for generated LM Hashes
 */

@Component({
  selector: "hash-gen",
  templateUrl: "hash-gen.component.html",
  styleUrls: ['../../../node_modules/bootstrap/dist/css/bootstrap.min.css']
})
export class HashGenComponent{
  hashFound: boolean = false;
  hasApiError: boolean = false;
  secret: Secret = new Secret();
  secrets : Secret [] = [];
  error: ApiError = new ApiError();
  genForm: FormGroup;
  plaintextAlert: string = "Password must be less than 14 characters and contain only printable ASCII characters";

  constructor(private repository: SecretRepository, private formBuilder: FormBuilder){
    this.genForm = formBuilder.group({
      'plaintext' : [null, Validators.compose(
              [Validators.required,
                        Validators.maxLength(14),
                        Validators.minLength(1)]
      )]
    })
  }

  submitForm(form){
    this.secret = this.repository.getSecretByPlaintext(form.plaintext);
    this.hashFound = true;
  }
}

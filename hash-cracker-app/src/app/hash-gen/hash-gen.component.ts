
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
  working: boolean = false;
  hashFound: boolean = false;
  hasApiError: boolean = false;
  secret: Secret;
  apiError: ApiError;
  genForm: FormGroup;
  plaintextAlert: string = "Password must be less than 14 characters and contain only printable ASCII characters";
  buttonTextWorking: string = "Generating Hash";
  buttonTextReady: string = "Generate Hash";


  constructor(private repository: SecretRepository, private formBuilder: FormBuilder){
    this.genForm = formBuilder.group({
      'plaintext' : [null, Validators.compose(
              [Validators.required,
                        Validators.maxLength(14), //change back to 14
                        Validators.minLength(1)]
      )]
    })
  }

  submitForm(form){
    this.hashFound = false;
    this.working = true;
    this.repository.getSecretByPlaintext(form.plaintext)
        .subscribe(res => {
              this.secret = res;
              this.hashFound = true;
              this.hasApiError = false;
            },
            err => {
              console.log(err);
              this.apiError = err.error;
              this.hasApiError = true;
              this.hashFound = false;
            }, () =>
          {
            this.working = false;
          });


  }
}

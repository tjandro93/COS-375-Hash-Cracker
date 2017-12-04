
import {NgModule} from "@angular/core";
import {ModelModule} from "../model/model.module";
import {HashCrackComponent} from "./hash-crack.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [ModelModule, ReactiveFormsModule, CommonModule, FormsModule],
  declarations: [HashCrackComponent],
  exports: [HashCrackComponent]
})
export class HashCrackModule {}

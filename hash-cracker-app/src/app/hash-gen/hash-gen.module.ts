
import {NgModule} from "@angular/core";
import { CommonModule } from "@angular/common";
import {HashGenComponent} from "./hash-gen.component";
import {ModelModule} from "../model/model.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  imports: [ModelModule, ReactiveFormsModule, CommonModule, FormsModule],
  declarations: [HashGenComponent],
  exports: [HashGenComponent]
})
export class HashGenModule {}

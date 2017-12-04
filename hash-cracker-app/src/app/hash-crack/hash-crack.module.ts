
import {NgModule} from "@angular/core";
import {ModelModule} from "../model/model.module";
import {HashCrackComponent} from "./hash-crack.component";

@NgModule({
  imports: [ModelModule],
  declarations: [HashCrackComponent],
  exports: [HashCrackComponent]
})
export class HashCrackModule {}

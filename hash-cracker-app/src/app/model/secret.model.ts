import {LmHash} from "./lm-hash.model";

/*
Model of a Secret which may be requested by the user
 */

export class Secret {
  constructor(public id?: number, public plaintext?: string, public lmHash?: LmHash){}
}

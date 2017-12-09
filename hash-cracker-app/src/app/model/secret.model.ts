import {LmHash} from "./lm-hash.model";

export class Secret {
  constructor(public id?: number, public plaintext?: string, public lmHash?: LmHash){}
}

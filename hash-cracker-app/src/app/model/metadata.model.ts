export class Metadata {
  constructor(public id?: number,
              public timesRequested?: number,
              public instantFound?: number,
              public secondsToFind?: number,
              public hasBeenFound?: boolean) {}
}

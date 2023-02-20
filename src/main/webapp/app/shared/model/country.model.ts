import { IState } from '@/shared/model/state.model';

export interface ICountry {
  id?: number;
  name?: string | null;
  states?: IState[] | null;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string | null, public states?: IState[] | null) {}
}

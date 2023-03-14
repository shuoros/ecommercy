import { ICity } from '@/shared/model/city.model';
import { ICountry } from '@/shared/model/country.model';

export interface IState {
  id?: number;
  name?: string | null;
  cities?: ICity[] | null;
  country?: ICountry | null;
}

export class State implements IState {
  constructor(public id?: number, public name?: string | null, public cities?: ICity[] | null, public country?: ICountry | null) {}
}

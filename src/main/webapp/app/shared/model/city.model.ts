import { IState } from '@/shared/model/state.model';

export interface ICity {
  id?: number;
  name?: string | null;
  state?: IState | null;
}

export class City implements ICity {
  constructor(public id?: number, public name?: string | null, public state?: IState | null) {}
}

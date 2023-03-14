import { ICategory } from '@/shared/model/category.model';

export interface IGroup {
  id?: number;
  name?: string | null;
  categories?: ICategory[] | null;
}

export class Group implements IGroup {
  constructor(public id?: number, public name?: string | null, public categories?: ICategory[] | null) {}
}

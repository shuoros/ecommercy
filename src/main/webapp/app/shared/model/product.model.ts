import { IPicture } from '@/shared/model/picture.model';
import { IProductMainAttribute } from '@/shared/model/product-main-attribute.model';
import { IComment } from '@/shared/model/comment.model';
import { IAttribute } from '@/shared/model/attribute.model';
import { IItem } from '@/shared/model/item.model';
import { ICategory } from '@/shared/model/category.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  brand?: string | null;
  rate?: number | null;
  description?: string | null;
  mainPic?: IPicture | null;
  productMainAttribute?: IProductMainAttribute | null;
  comments?: IComment[] | null;
  pictures?: IPicture[] | null;
  attributes?: IAttribute[] | null;
  items?: IItem[] | null;
  categories?: ICategory[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string | null,
    public brand?: string | null,
    public rate?: number | null,
    public description?: string | null,
    public mainPic?: IPicture | null,
    public productMainAttribute?: IProductMainAttribute | null,
    public comments?: IComment[] | null,
    public pictures?: IPicture[] | null,
    public attributes?: IAttribute[] | null,
    public items?: IItem[] | null,
    public categories?: ICategory[] | null
  ) {}
}

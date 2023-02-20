/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CategoryComponent from '@/entities/category/category.vue';
import CategoryClass from '@/entities/category/category.component';
import CategoryService from '@/entities/category/category.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Category Management Component', () => {
    let wrapper: Wrapper<CategoryClass>;
    let comp: CategoryClass;
    let categoryServiceStub: SinonStubbedInstance<CategoryService>;

    beforeEach(() => {
      categoryServiceStub = sinon.createStubInstance<CategoryService>(CategoryService);
      categoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CategoryClass>(CategoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          categoryService: () => categoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      categoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCategorys();
      await comp.$nextTick();

      // THEN
      expect(categoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.categories[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      categoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(categoryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCategory();
      await comp.$nextTick();

      // THEN
      expect(categoryServiceStub.delete.called).toBeTruthy();
      expect(categoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

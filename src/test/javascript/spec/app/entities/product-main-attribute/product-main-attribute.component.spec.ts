/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProductMainAttributeComponent from '@/entities/product-main-attribute/product-main-attribute.vue';
import ProductMainAttributeClass from '@/entities/product-main-attribute/product-main-attribute.component';
import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';
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
  describe('ProductMainAttribute Management Component', () => {
    let wrapper: Wrapper<ProductMainAttributeClass>;
    let comp: ProductMainAttributeClass;
    let productMainAttributeServiceStub: SinonStubbedInstance<ProductMainAttributeService>;

    beforeEach(() => {
      productMainAttributeServiceStub = sinon.createStubInstance<ProductMainAttributeService>(ProductMainAttributeService);
      productMainAttributeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProductMainAttributeClass>(ProductMainAttributeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          productMainAttributeService: () => productMainAttributeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      productMainAttributeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProductMainAttributes();
      await comp.$nextTick();

      // THEN
      expect(productMainAttributeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.productMainAttributes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      productMainAttributeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(productMainAttributeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeProductMainAttribute();
      await comp.$nextTick();

      // THEN
      expect(productMainAttributeServiceStub.delete.called).toBeTruthy();
      expect(productMainAttributeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

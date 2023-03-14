/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AttributeComponent from '@/entities/attribute/attribute.vue';
import AttributeClass from '@/entities/attribute/attribute.component';
import AttributeService from '@/entities/attribute/attribute.service';
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
  describe('Attribute Management Component', () => {
    let wrapper: Wrapper<AttributeClass>;
    let comp: AttributeClass;
    let attributeServiceStub: SinonStubbedInstance<AttributeService>;

    beforeEach(() => {
      attributeServiceStub = sinon.createStubInstance<AttributeService>(AttributeService);
      attributeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AttributeClass>(AttributeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          attributeService: () => attributeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      attributeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAttributes();
      await comp.$nextTick();

      // THEN
      expect(attributeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.attributes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      attributeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(attributeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAttribute();
      await comp.$nextTick();

      // THEN
      expect(attributeServiceStub.delete.called).toBeTruthy();
      expect(attributeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
